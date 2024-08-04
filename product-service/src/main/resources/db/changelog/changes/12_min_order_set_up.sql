--liquibase formatted sql
--changeset ritik:setting_min_order_price
--comment: setting minimum order price and updating delivery charges.

INSERT INTO `system_config`
(`id`, `created_at`, `created_by`, `deleted`, `modified_at`, `modified_by`, `config_key`, `config_value`)
VALUES
(10, '2024-08-02 05:29:17', 'ritik', 0, '2024-08-02 05:29:17', 'ritik', 'min.order.amount', '799'),
(11, '2024-08-03 05:29:17', 'ritik', 0, '2024-08-03 05:29:17', 'ritik', 'max.delivery.chargeable.order.amount', '1199');

UPDATE `system_config`
SET
    `config_value` = '30'
WHERE
    `id` = '2';