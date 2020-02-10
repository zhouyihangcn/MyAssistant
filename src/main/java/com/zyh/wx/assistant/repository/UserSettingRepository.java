package com.zyh.wx.assistant.repository;

import org.springframework.data.repository.CrudRepository;
import com.zyh.wx.assistant.entity.UserSetting;


public interface UserSettingRepository extends CrudRepository<UserSetting, String> {

}
