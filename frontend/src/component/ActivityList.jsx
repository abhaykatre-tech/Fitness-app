import { Card, CardContent, Grid, Typography } from "@mui/material";
// Note: Grid2 is now imported directly as a named export

import React, { useEffect, useState } from "react";
//import { useNavigate } from "react-router";
import { getActivities } from "../services/api";
import { useNavigate } from "react-router-dom";

const ActivityList = () => {
  const [activities, setActivities] = useState([]);
  const navigate = useNavigate();

  const fetchActivities = async () => {
    try {
      const response = await getActivities();
      setActivities(response.data);
    } catch (error) {
      console.error(error);
    }
  };

  useEffect(() => {
    fetchActivities();
  }, []);
  return (
    <Grid
      container
      spacing={2}
      sx={{
        justifyContent: "center",
        marginTop: 4,
        backgroundColor: "#1668baff",
      }}
    >
      {activities.map((activity) => (
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
            onClick={() =>
              navigate("/activity", { state: { activityId: activity.id } })
            }
            id={activity.id}
          >
            <CardContent>
              <Typography variant="h6">{activity.type}</Typography>
              <Typography>Duration: {activity.duration}</Typography>

              <Typography>
                Steps:
                {activity.additionalMetrics.steps}
              </Typography>
              <Typography>Calories: ......</Typography>
            </CardContent>
          </Card>
        </Grid>
      ))}
    </Grid>
  );
};

export default ActivityList;
