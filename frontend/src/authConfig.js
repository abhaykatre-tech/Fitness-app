const authConfig = {
  clientId: "outh2-pkce-client",
  authorizationEndpoint:
    "http://localhost:8181/realms/fitness-apps/protocol/openid-connect/auth",
  tokenEndpoint:
    "http://localhost:8181/realms/fitness-apps/protocol/openid-connect/token",

  redirectUri: "http://localhost:3000", // or your frontend domain
  scope: "openid profile email offline_access",
  onRefreshTokenExpire: (event) => event.logIn(),
};

export default authConfig;
