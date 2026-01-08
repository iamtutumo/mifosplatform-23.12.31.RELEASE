-- Migration: add transaction metadata tables and columns (MariaDB)
-- Informational metadata only; does not modify financial columns
CREATE TABLE IF NOT EXISTS m_transaction_category (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(100) NOT NULL,
  description TEXT,
  is_system BOOLEAN DEFAULT FALSE,
  is_active BOOLEAN DEFAULT TRUE,
  created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  last_modified_date TIMESTAMP NULL,
  created_by_id BIGINT,
  last_modified_by_id BIGINT,
  UNIQUE KEY unique_category_name (name)
);

CREATE TABLE IF NOT EXISTS m_transaction_tag (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(100) NOT NULL,
  description TEXT,
  color_code VARCHAR(7),
  created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  created_by_id BIGINT,
  UNIQUE KEY unique_tag_name (name)
);

CREATE TABLE IF NOT EXISTS m_transaction_tags (
  transaction_id BIGINT NOT NULL,
  tag_id BIGINT NOT NULL,
  PRIMARY KEY (transaction_id, tag_id),
  CONSTRAINT fk_tx_tags_transaction FOREIGN KEY (transaction_id) REFERENCES m_savings_account_transaction(id),
  CONSTRAINT fk_tx_tags_tag FOREIGN KEY (tag_id) REFERENCES m_transaction_tag(id)
);

-- Add columns to savings transaction table (if they don't exist)
SET @alter_sql = CONCAT(
  IF((SELECT COUNT(*) FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = 'fineract_default' AND TABLE_NAME = 'm_savings_account_transaction' AND COLUMN_NAME = 'category_id') = 0,
    'ALTER TABLE m_savings_account_transaction ADD COLUMN category_id BIGINT NULL;', '')
);
PREPARE alter_stmt FROM @alter_sql;
EXECUTE alter_stmt;
DEALLOCATE PREPARE alter_stmt;

SET @alter_sql = CONCAT(
  IF((SELECT COUNT(*) FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = 'fineract_default' AND TABLE_NAME = 'm_savings_account_transaction' AND COLUMN_NAME = 'depositor_name') = 0,
    'ALTER TABLE m_savings_account_transaction ADD COLUMN depositor_name VARCHAR(100) NULL;', '')
);
PREPARE alter_stmt FROM @alter_sql;
EXECUTE alter_stmt;
DEALLOCATE PREPARE alter_stmt;

SET @alter_sql = CONCAT(
  IF((SELECT COUNT(*) FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = 'fineract_default' AND TABLE_NAME = 'm_savings_account_transaction' AND COLUMN_NAME = 'depositor_relationship') = 0,
    'ALTER TABLE m_savings_account_transaction ADD COLUMN depositor_relationship VARCHAR(50) NULL;', '')
);
PREPARE alter_stmt FROM @alter_sql;
EXECUTE alter_stmt;
DEALLOCATE PREPARE alter_stmt;

-- Add foreign key constraint (if it doesn't exist)
SET @fk_exists = (SELECT COUNT(*) FROM INFORMATION_SCHEMA.REFERENTIAL_CONSTRAINTS 
  WHERE CONSTRAINT_SCHEMA = 'fineract_default' AND CONSTRAINT_NAME = 'fk_savings_tx_category');
SET @add_fk = IF(@fk_exists = 0, 
  'ALTER TABLE m_savings_account_transaction ADD CONSTRAINT fk_savings_tx_category FOREIGN KEY (category_id) REFERENCES m_transaction_category(id);',
  'SELECT 1;');
PREPARE fk_stmt FROM @add_fk;
EXECUTE fk_stmt;
DEALLOCATE PREPARE fk_stmt;
