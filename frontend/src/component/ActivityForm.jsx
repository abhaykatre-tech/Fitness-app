import {
  Box,
  Button,
  FormControl,
  InputLabel,
  MenuItem,
  Select,
  TextField,
} from "@mui/material";
import React, { useState } from "react";
import { addActivity } from "../services/api";

const ActivityForm = () => {
  const [activity, setActivity] = useState({
    type: "",
    duration: "",
    caloriesBurned: "",
    additionalMetrics: {
      distance: "",
      averageHeartRate: "",
      steps: "",  
      location: "",
    },
  });

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await addActivity(activity);
      setActivity({
        type: "",
        duration: "",
        caloriesBurned: "",
        additionalMetrics: {
          distance: "",
          averageHeartRate: "",
          steps: "",
          location: "",
        },
      });
      console.log("Form cleared:", activity);
    } catch (error) {
      console.error(error);
    }
  };

  return (
    <Box component="form" onSubmit={handleSubmit} sx={{ mb: 4 }}>
      <FormControl fullWidth sx={{ mb: 2, mt: 4 }}>
        <InputLabel>Activity Type</InputLabel>
        <Select
          value={activity.type}
          onChange={(e) => setActivity({ ...activity, type: e.target.value })}
        >
          <MenuItem value="RUNNING">Running</MenuItem>
          <MenuItem value="WALKING">Walking</MenuItem>
          <MenuItem value="CYCLING">Cycling</MenuItem>
        </Select>
      </FormControl>
      <TextField
        required="true"
        fullWidth
        label="Duration (Minutes)"
        type="number"
        sx={{ mb: 2 }}
        value={activity.duration}
        onChange={(e) => setActivity({ ...activity, duration: e.target.value })}
      />

      <TextField
        fullWidth
        label="Calories Burned"
        type="number"
        sx={{ mb: 2 }}
        value={activity.caloriesBurned}
        onChange={(e) =>
          setActivity({ ...activity, caloriesBurned: e.target.value })
        }
      />

      <TextField
        fullWidth
        label="location"
        type="text"
        sx={{ mb: 2 }}
        value={activity.additionalMetrics.location}
        onChange={(e) =>
          setActivity({
            ...activity,
            additionalMetrics: {
              ...activity.
              additionalMetrics,location: e.target.value,
            },
          })
        }
      />
      <TextField
        fullWidth
        label="Steps"
        type="number"
        sx={{ mb: 2 }}
        value={activity.additionalMetrics.steps}
        onChange={(e) =>
          setActivity({
            ...activity,
            additionalMetrics: {
              ...activity.
              additionalMetrics,
              steps: e.target.value,
            },
          })
        }
      />

      <Button type="submit" variant="contained">
        Add Activity
      </Button>
    </Box>
  );
};

export default ActivityForm;
