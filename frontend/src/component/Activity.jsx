import React, { useEffect, useState } from "react";
import { useLocation } from "react-router-dom";
import { loadActivityAccordingId } from "../services/api";
import {
  Card,
  CardContent,
  Divider,
  Grid,
  Typography,
  CircularProgress,
  Box,
} from "@mui/material";

function Activity() {
  const [activities, setActivities] = useState(null);
  const [loading, setLoading] = useState(true);
  const location = useLocation();
  const { activityId } = location.state || {};

  // --- API CALL FUNCTION ---
  const fetchActivity = async (activityId) => {
    try {
      const response = await loadActivityAccordingId(activityId);
      console.log("Response:", response.data);
      // Agar response me actual data mila to state update
      if (response.data && response.data.activityId) {
        setActivities(response.data);
        setLoading(false);
        return true; // success
      }
      return false; // data not yet ready
    } catch (error) {
      console.log("Error while fetching activity:", error);
      return false;
    }
  };

  // --- EFFECT FOR POLLING UNTIL DATA READY ---
  useEffect(() => {
    if (!activityId) return;

    let intervalId;
    let attempts = 0;
    const maxAttempts = 15; // max wait (10 * 2s = 20 seconds)

    const startPolling = async () => {
      const success = await fetchActivity(activityId);
      if (!success) {
        intervalId = setInterval(async () => {
          attempts++;
          console.log(`⏳ Retrying fetch... attempt ${attempts}`);
          const successNow = await fetchActivity(activityId);
          if (successNow || attempts >= maxAttempts) {
            clearInterval(intervalId);
            setLoading(false);
          }
        }, 2000); // check every 2 seconds
      }
    };

    startPolling();

    return () => clearInterval(intervalId); // cleanup
  }, [activityId]);

  // --- LOADING UI ---
  if (loading) {
    return (
      <Box
        sx={{
          height: "100vh",
          display: "flex",
          flexDirection: "column",
          alignItems: "center",
          justifyContent: "center",
          backgroundColor: "#f5f5f5",
        }}
      >
        <CircularProgress size={60} thickness={5} sx={{ color: "#1976d2" }} />
        <Typography variant="h6" sx={{ marginTop: 2, color: "#333" }}>
          Waiting for activity data to be generated...
        </Typography>
      </Box>
    );
  }

  // --- NO DATA (EVEN AFTER POLLING) ---
  if (!activities) {
    return (
      <Box
        sx={{
          height: "100vh",
          display: "flex",
          flexDirection: "column",
          alignItems: "center",
          justifyContent: "center",
          backgroundColor: "#f5f5f5",
        }}
      >
        <Typography variant="h6" sx={{ color: "red" }}>
          ⚠️ Data not available yet. Please try again later.
        </Typography>
      </Box>
    );
  }

  // --- ACTUAL UI ---
  return (
    <Grid
      container
      spacing={2}
      sx={{
        justifyContent: "center",
        marginTop: 4,
        backgroundColor: "#1668baff",
        minHeight: "100vh",
        padding: 3,
      }}
    >
      <Grid
        container
        spacing={{ xs: 2, md: 3 }}
        columns={{ xs: 4, sm: 8, md: 12 }}
      >
        <Card
          sx={{
            cursor: "pointer",
            minWidth: 325,
            margin: 2,
            color: "#e3d8d8ff",
            backgroundColor: "#070707d0",
          }}
        >
          <CardContent>
            <Typography>id: {activities.activityId}</Typography>
            <Typography>
              Date: {new Date(activities.createdAt).toLocaleString()}
            </Typography>
            <Typography variant="h6">{activities.activityType}</Typography>
            <Divider sx={{ my: 2 }} />

            <Typography variant="h6" sx={{ color: "greenyellow" }}>
              RECOMMENDATION
            </Typography>
            <Divider sx={{ my: 2 }} />
            <Typography>{activities.recommendations}</Typography>
            <Divider sx={{ my: 2 }} />

            <Typography variant="h6" sx={{ color: "greenyellow" }}>
              IMPROVEMENTS
            </Typography>
            {activities?.improvement?.map((imp, index) => (
              <Typography key={index}>• {imp}</Typography>
            ))}
            <Divider sx={{ my: 2 }} />

            <Typography variant="h6" sx={{ color: "greenyellow" }}>
              Suggestions
            </Typography>
            {activities?.suggestions?.map((suggestion, index) => (
              <Typography key={index} paragraph>
                • {suggestion}
              </Typography>
            ))}
            <Divider sx={{ my: 2 }} />

            <Typography variant="h6" sx={{ color: "orange" }}>
              Safety Guidelines (most important)
            </Typography>
            {activities?.safety?.map((safety, index) => (
              <Typography key={index} paragraph>
                • {safety}
              </Typography>
            ))}
          </CardContent>
        </Card>
      </Grid>
    </Grid>
  );
}

export default Activity;
