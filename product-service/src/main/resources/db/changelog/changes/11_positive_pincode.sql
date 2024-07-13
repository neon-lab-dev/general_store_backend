--liquibase formatted sql
--changeset ritik:adding-positive-pincode
--comment: catering to some positive pincode only if enabled.

INSERT INTO `system_config`
(`id`, `created_at`, `created_by`, `deleted`, `modified_at`, `modified_by`, `config_key`, `config_value`)
VALUES
(8, '2024-05-07 05:29:17', 'ritik', 0, '2024-05-07 05:29:17', 'ritik', 'positive.pincode.available', 'false'),
(9, '2024-05-07 05:29:17', 'ritik', 0, '2024-05-07 05:29:17', 'ritik', 'positive.pincode.list', '766001, 400021');0