import { boot } from 'quasar/wrappers'

const Constants = {
    CALLBACK_TO_APP_KEY: "cb_to_app",
    TOKEN_KEY: "cm_tk",
    CONSUMER_INFO_KEY: "cm_if",
};
 
export default boot(({ app }) => {    
    app.config.globalProperties.$Constants = Constants
});

export { Constants };