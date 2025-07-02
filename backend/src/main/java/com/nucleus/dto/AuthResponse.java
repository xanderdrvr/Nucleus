package com.nucleus.dto;

public record AuthResponse (
   String token,

   String displayName,

   String avatarUrl
) {}
