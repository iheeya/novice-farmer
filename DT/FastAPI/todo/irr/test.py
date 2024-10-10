import os
from aquacrop import AquaCropModel, Soil, Crop, InitialWaterContent, FieldMngt, GroundWater
from aquacrop.utils import prepare_weather, get_filepath

import pandas as pd
import matplotlib.pyplot as plt

os.environ['DEVELOPMENT'] = 'True'