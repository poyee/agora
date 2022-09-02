package com.poyee.agora.bean;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class SignUpRequest {
	private String providerUserId;

	private String email;

	private String displayName;
}
