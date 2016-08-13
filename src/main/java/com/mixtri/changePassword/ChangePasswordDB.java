package com.mixtri.changePassword;

import com.mixtri.utils.MixtriUtils;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

public class ChangePasswordDB {

	public String getChangePasswordHashCode(String recipientEmailId){
		String hashCode;
		
		hashCode = MixtriUtils.getUUID();
		return hashCode;
	}
}
