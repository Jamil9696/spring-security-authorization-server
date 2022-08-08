
--Project-
CREATE SCHEMA pm;

    REVOKE ALL ON SCHEMA pm FROM PUBLIC;
    REVOKE ALL ON DATABASE amber_dev FROM PUBLIC;

-- flyway user --
CREATE USER amber_dev_pm_dbo WITH PASSWORD 'admin' ;

-- pm-service-user
CREATE USER amber_dev_pm_dbu WITH PASSWORD 'user';

-- global roles --

-- readonly access --
CREATE ROLE pm_read_access;
-- permissions to access resources (in order to read, you need to have at least access permissions) --
    GRANT CONNECT ON DATABASE amber_dev TO pm_read_access;
    GRANT USAGE ON SCHEMA pm TO pm_read_access;

-- future table access
CREATE ROLE enable_future_table_access;
-- every new relation has to be accessible by dbu
    ALTER DEFAULT PRIVILEGES FOR USER amber_dev_pm_dbo IN SCHEMA pm
    GRANT SELECT, INSERT, UPDATE, DELETE ON TABLES TO enable_future_table_access;
-- hibernate is also trying to access the sequence generator
    ALTER DEFAULT PRIVILEGES FOR USER amber_dev_pm_dbo IN SCHEMA pm
    GRANT SELECT, UPDATE ON SEQUENCES TO enable_future_table_access;

    --dbo-user rights --

-- admin role --
CREATE ROLE pm_admin;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA pm TO pm_admin;


-- admin --
GRANT pm_read_access TO amber_dev_pm_dbo;
GRANT pm_admin TO amber_dev_pm_dbo;

--Technical User --
GRANT pm_read_access TO amber_dev_pm_dbu;
GRANT enable_future_table_access TO amber_dev_pm_dbu;

alter schema pm owner to amber_dev_pm_dbo;


