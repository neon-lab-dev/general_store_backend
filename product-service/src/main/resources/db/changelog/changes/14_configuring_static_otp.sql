--liquibase formatted sql
--changeset ritik:configuring_static_otp
--comment: setting static otp

INSERT INTO `system_config`
(`id`, `created_at`, `created_by`, `deleted`, `modified_at`, `modified_by`, `config_key`, `config_value`)
VALUES
(13, '2024-08-03 05:29:17', 'ritik', 0, '2024-08-03 05:29:17', 'ritik', 'static.otp.enabled', 'true'),
(14, '2024-08-03 05:29:17', 'ritik', 0, '2024-08-03 05:29:17', 'ritik', 'static.otp', '9999');