SET autocommit=0;
DROP SCHEMA IF EXISTS `ERP`;
CREATE SCHEMA `ERP`;
DROP SCHEMA IF EXISTS `CIT`;
CREATE SCHEMA `CIT`;
DROP TABLE IF EXISTS `ERP`.`Employee`;
CREATE TABLE `ERP`.`Employee`(
	`num`VARCHAR(100),
	`gender`VARCHAR(100),
	`name`VARCHAR(100),
	`position`VARCHAR(100)
	);
DROP TABLE IF EXISTS `ERP`.`Product`;
CREATE TABLE `ERP`.`Product`(
	`num`VARCHAR(100),
	`category`VARCHAR(100),
	`name`VARCHAR(100),
	`price`DECIMAL(28,12)
	);
DROP TABLE IF EXISTS `ERP`.`Customer`;
CREATE TABLE `ERP`.`Customer`(
	`num`VARCHAR(100),
	`name`VARCHAR(100)
	);
DROP TABLE IF EXISTS `ERP`.`Vendor`;
CREATE TABLE `ERP`.`Vendor`(
	`num`VARCHAR(100),
	`name`VARCHAR(100)
	);
DROP TABLE IF EXISTS `ERP`.`Logistics`;
CREATE TABLE `ERP`.`Logistics`(
	`num`VARCHAR(100),
	`name`VARCHAR(100)
	);
DROP TABLE IF EXISTS `ERP`.`Quotation`;
CREATE TABLE `ERP`.`SalesQuotation`(
	`num`VARCHAR(100),
	`date`DATE,
	`sentBy`VARCHAR(100),
	`sentTo`VARCHAR(100),
	`status`VARCHAR(100)
	);
DROP TABLE IF EXISTS `ERP`.`QuotationLine`;
CREATE TABLE `ERP`.`SalesQuotationLine`(
	`contains`VARCHAR(100),
	`partOf`VARCHAR(100),
	`purchPrice`DECIMAL(28,12),
	`quantity`INT,
	`salesPrice`DECIMAL(28,12)
	);
DROP TABLE IF EXISTS `ERP`.`SalesOrder`;
CREATE TABLE `ERP`.`SalesOrder`(
	`num`VARCHAR(100),
	`basedOn`VARCHAR(100),
	`date`DATE,
	`deliveryDate`DATE,
	`processedBy`VARCHAR(100),
	`receivedFrom`VARCHAR(100)
	);
DROP TABLE IF EXISTS `ERP`.`SalesOrderLine`;
CREATE TABLE `ERP`.`SalesOrderLine`(
	`contains`VARCHAR(100),
	`partOf`VARCHAR(100),
	`quantity`DECIMAL(28,12),
	`salesPrice`DECIMAL(28,12)
	);
DROP TABLE IF EXISTS `ERP`.`SalesInvoice`;
CREATE TABLE `ERP`.`SalesInvoice`(
	`num`VARCHAR(100),
	`createdFor`VARCHAR(100),
	`date`DATE,
	`revenue`FLOAT,
	`text`VARCHAR(100)
	);
DROP TABLE IF EXISTS `ERP`.`PurchOrder`;
CREATE TABLE `ERP`.`PurchOrder`(
	`num`VARCHAR(100),
	`date`DATE,
	`placedAt`VARCHAR(100),
	`processedBy`VARCHAR(100),
	`serves`VARCHAR(100)
	);
DROP TABLE IF EXISTS `ERP`.`PurchOrderLine`;
CREATE TABLE `ERP`.`PurchOrderLine`(
	`contains`VARCHAR(100),
	`partOf`VARCHAR(100),
	`purchPrice`DECIMAL(28,12),
	`quantity`DECIMAL(28,12)
	);
DROP TABLE IF EXISTS `ERP`.`DeliveryNote`;
CREATE TABLE `ERP`.`DeliveryNote`(
	`num`VARCHAR(100),
	`contains`VARCHAR(100),
	`date`DATE,
	`operatedBy`VARCHAR(100),
	`trackingCode`VARCHAR(100)
	);
DROP TABLE IF EXISTS `ERP`.`PurchInvoice`;
CREATE TABLE `ERP`.`PurchInvoice`(
	`num`VARCHAR(100),
	`createdFor`VARCHAR(100),
	`date`DATE,
	`expense`DECIMAL(28,12),
	`text`VARCHAR(100)
	);
DROP TABLE IF EXISTS `CIT`.`User`;
CREATE TABLE `CIT`.`User`(
	`email`VARCHAR(100),
	`erpEmplNum`VARCHAR(100),
	`name`VARCHAR(100)
	);
DROP TABLE IF EXISTS `CIT`.`Client`;
CREATE TABLE `CIT`.`Client`(
	`account`VARCHAR(100),
	`contactPhone`VARCHAR(100),
	`erpCustNum`VARCHAR(100),
	`name`VARCHAR(100)
	);
	DROP TABLE IF EXISTS `CIT`.`Ticket`;
CREATE TABLE `CIT`.`Ticket`(
	`id`INT,
	`allocatedTo`VARCHAR(100),
	`createdAt`DATE,
	`createdBy`VARCHAR(100),
	`erpSoNum`VARCHAR(100),
	`openedBy`VARCHAR(100),
	`problem`VARCHAR(100)
	);
COMMIT;