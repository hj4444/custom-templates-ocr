package com.qk365.ocr.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ResultCodeEnum implements ResultCode {
	SUCCESS(HttpStatus.OK.value(), "操作成功"),
	FAILURE(HttpStatus.BAD_REQUEST.value(), "业务异常"),
	NOT_FOUND(HttpStatus.NOT_FOUND.value(), "404 没找到请求"),
	REQ_REJECT(HttpStatus.FORBIDDEN.value(), "请求被拒绝"),
	INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), "服务器异常"),
	PARAM_ERROR(501, "参数错误"),
	SQL_ERROR(502, "SQL错误");
	final int code;
	final String message;
}
