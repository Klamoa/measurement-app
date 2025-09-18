-- needed as timescaleDB needs PK to include "date"
ALTER TABLE measurement DROP CONSTRAINT IF EXISTS measurement_pkey;
ALTER TABLE measurement ADD CONSTRAINT measurement_pkey PRIMARY KEY (measurementid, date);

SELECT create_hypertable('measurement', 'date');

CREATE INDEX IF NOT EXISTS idx_measurement_sensor_id ON measurement(sensorid);
CREATE INDEX IF NOT EXISTS idx_measurement_date ON measurement(date);