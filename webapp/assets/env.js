(function(window) {
  window["env"] = window["env"] || {};

  // BackEnd Environment variables
  window["env"]["fineractApiUrls"] = 'http://localhost:8080';
  window["env"]["fineractApiUrl"]  = 'http://localhost:8080';

  window["env"]["apiProvider"] = '/fineract-provider/api';
  window["env"]["apiVersion"]  = '/v1';

  window["env"]["fineractPlatformTenantId"]  = 'default';
  window["env"]["fineractPlatformTenantIds"]  = 'default';

  // Language Environment variables
  window["env"]["defaultLanguage"] = 'en-US';
  window["env"]["supportedLanguages"] = 'cs-CS,de-DE,en-US,es-MX,fr-FR,it-IT,ko-KO,li-LI,lv-LV,ne-NE,pt-PT,sw-SW';

  window['env']['preloadClients'] = 'true';

  // Char delimiter to Export CSV options: ',' ';' '|' ' '
  window['env']['defaultCharDelimiter'] = ',';

  // Display or not the BackEnd Info
  window['env']['displayBackEndInfo'] = 'true';

})(this);
