-- drop database holidays;
-- drop role holidays;

create role holidays LOGIN PASSWORD 'holidays';
create database holidays owner holidays;