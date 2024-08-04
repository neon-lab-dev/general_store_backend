--liquibase formatted sql
--changeset ritik:setting_reach_out_path
--comment: setting reach out content page path

INSERT INTO `system_config`
(`id`, `created_at`, `created_by`, `deleted`, `modified_at`, `modified_by`, `config_key`, `config_value`)
VALUES
(12, '2024-08-03 05:29:17', 'ritik', 0, '2024-08-03 05:29:17', 'ritik', 'reach.out.html.path', '');