
import { createRoot } from 'react-dom/client'
import './index.css'
import App from './App.jsx'
import { Provider, Provider as provider } from 'react-redux';
import store from './store.js';
import { AuthProvider } from "react-oauth2-code-pkce";
import authConfig from "./authConfig.js";

createRoot(document.getElementById("root")).render(
  <AuthProvider authConfig={authConfig}>
    <Provider store={store}>
      <App />
    </Provider>
  </AuthProvider>
);
