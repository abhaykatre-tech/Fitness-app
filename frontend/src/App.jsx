import { useContext, useEffect, useState } from "react";
import { BrowserRouter as Router, Route, Routes,useLocation, Link, BrowserRouter} from "react-router-dom";
import "./App.css";
import Button from '@mui/material/Button';
import { setCredentials } from "./authSlice";
import { AuthContext } from "react-oauth2-code-pkce";
import { useDispatch } from "react-redux";
import Activity from "./component/Activity";
import ActivityList from "./component/ActivityList";
import ActivityForm from "./component/ActivityForm";
import Home from "./component/Home";

function App() {
  const { token, tokenData, logIn, logOut, isAuthenticated } = useContext(AuthContext);
  const dispatch = useDispatch()
  const [authReady, setAuthReady] = useState(false);
  useEffect(() => {
    if (token) { 
      dispatch(setCredentials({ token, user: tokenData }));
      setAuthReady(true);
    }
   }
    , [token, tokenData, dispatch]);
  return (
    <>
      <BrowserRouter>
        {!token ? (
          <div class="login">
            <Button
              variant="contained"
              color="success"
              onClick={() => logIn()}
              class="anti"
            >
              LOGIN
            </Button>
          </div>
        ) : (
          <div component="section">
            <nav className="nav1">
              <h3 className="logo1">FITNESS-APP</h3>

              <div className="nav-links1">
                <Link to="/home" className="link1">
                  HOME
                </Link>
                <Link to="/activityList" className="link1">
                  ACTIVITY LIST
                </Link>
                <Link to="/activityForm" className="link1">
                  ACTIVITY FORM
                </Link>
                <Link className="link1" onClick={() => logOut()}>
                  LOGOUT
                </Link>
              </div>
            </nav>

            <Routes>
              <Route path="/" element={<Home />} /> {/* ✅ Default route */}
              <Route path="/activity" element={<Activity />} />
              <Route path="/activityList" element={<ActivityList />} />
              <Route path="/activityForm" element={<ActivityForm />} />
              <Route path="/home" element={<Home />} />
              <Route path="*" element={<Home />} />
            </Routes>
          </div>
        )}
      </BrowserRouter>
    </>
  );
}
export default App;