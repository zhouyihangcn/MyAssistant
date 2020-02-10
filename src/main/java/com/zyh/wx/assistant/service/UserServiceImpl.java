package com.zyh.wx.assistant.service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.zyh.wx.assistant.entity.UserSetting;
import com.zyh.wx.assistant.repository.UserSettingRepository;
import com.zyh.wx.assistant.util.Constant;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

	private final UserSettingRepository userSettingRepository;

	@Override
	public ZoneOffset getZoneOffset(String user) {
		Optional<UserSetting> userSettingResult = userSettingRepository.findById(user);

		if (userSettingResult.isPresent()) {
			int offsetHour=userSettingResult.get().getZoneOffsetHours();
			int offsetMinutes = userSettingResult.get().getZoneOffsetMinutes();
			return ZoneOffset.ofHoursMinutes(offsetHour, offsetMinutes);
		} else {
			return ZoneOffset.ofHoursMinutes(Constant.DEFAULT_OFFSET_HOURS, Constant.DEFAULT_OFFSET_MINUTES);
		}
		
	}

	@Override
	public String setZoneOffset(String user, String inputOffset, Date createTime) {
		log.info("setting zoneoffset: {},{},{}", user, inputOffset, createTime);
		List<Integer> offsets = maptoOffsets(inputOffset);
		if (offsets==null || offsets.size() ==0 ) {
			return "输入错误a："+inputOffset+"。要求（hh:mm）：hh +-18；mm+-59。";
		}
		Integer offsetHour=offsets.get(0);
		Integer offsetMinutes = 0;
		if (offsets.size()>1) {
			offsetMinutes = offsets.get(1);
		}
		try {
			ZoneOffset zoneOffset = ZoneOffset.ofHoursMinutes(offsetHour, offsetMinutes);
			log.info("trying to set offset: {}, {}", offsetHour, offsetMinutes);
			LocalDateTime localCreateDate = createTime.toInstant().atOffset(zoneOffset).toLocalDateTime();
			saveRepository(user, offsetHour, offsetMinutes);
			return "设置成功，你当前时间为："+localCreateDate;
		} catch (Exception e) {
			return "输入错误b："+inputOffset+"。要求（hh:mm）：hh +-18；mm+-59。";
		}
	}

	private void saveRepository(String user, Integer offsetHours, Integer offsetMinutes) {
		Optional<UserSetting> userSettingResult = userSettingRepository.findById(user);
		UserSetting userSetting = null;
		if (userSettingResult.isPresent()) {
			userSetting=userSettingResult.get();
		} else {
			userSetting = new UserSetting(user);
		}
		userSetting.setZoneOffsetHours(offsetHours);
		userSetting.setZoneOffsetMinutes(offsetMinutes);
		userSettingRepository.save(userSetting );		
	}

	private List<Integer> maptoOffsets(String inputOffset) {
		List<String> offsetStr= new ArrayList<String>(Arrays.asList(inputOffset.split(":")));
		List<Integer> offsets = new ArrayList<Integer>();
		boolean negative = false;
		for (String s :offsetStr ) {
			Integer i = Integer.valueOf(s);
			if (i<0) {
				negative = true;
			} else if(negative && i>0) {
				i=0-i;
			}
			offsets.add(i);
		}
		return offsets;
	}

}
