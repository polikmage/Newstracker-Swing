package org.mpo.newstracker.entity.dto;

public class CommonResponseDto {
    private String message;

    public CommonResponseDto() {
    }

    public CommonResponseDto(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
