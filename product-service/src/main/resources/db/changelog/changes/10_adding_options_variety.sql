--liquibase formatted sql
--changeset ritik:update-options-variety
--comment: Resolution of the variety liter and ltr

ALTER table `variety` modify column `type` enum('SIZE','WEIGHT','PACK_OF','PRICE', 'LITER');
ALTER table `variety` modify column `unit` enum('KG','GRAM','PCS','S','L','XL','M','RS', 'LTR');