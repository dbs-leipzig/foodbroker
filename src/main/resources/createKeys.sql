ALTER TABLE `ERP`.`Employee`
ADD PRIMARY KEY (`num`);
COMMIT;
ALTER TABLE `ERP`.`Product`
ADD PRIMARY KEY (`num`);
COMMIT;
ALTER TABLE `ERP`.`Customer`
ADD PRIMARY KEY (`num`);
COMMIT;
ALTER TABLE `ERP`.`Vendor`
ADD PRIMARY KEY (`num`);
COMMIT;
ALTER TABLE `ERP`.`Logistics`
ADD PRIMARY KEY (`num`);
COMMIT;
ALTER TABLE `ERP`.`SalesQuotation`
ADD PRIMARY KEY (`num`);
COMMIT;
ALTER TABLE `ERP`.`SalesOrder`
ADD PRIMARY KEY (`num`);
COMMIT;
ALTER TABLE `ERP`.`SalesInvoice`
ADD PRIMARY KEY (`num`);
COMMIT;
ALTER TABLE `ERP`.`PurchOrder`
ADD PRIMARY KEY (`num`);
COMMIT;
ALTER TABLE `ERP`.`DeliveryNote`
ADD PRIMARY KEY (`num`);
COMMIT;
ALTER TABLE `ERP`.`PurchInvoice`
ADD PRIMARY KEY (`num`);
COMMIT;
ALTER TABLE `CIT`.`User`
ADD PRIMARY KEY (`email`);
COMMIT;
ALTER TABLE `CIT`.`Client`
ADD PRIMARY KEY (`account`);
COMMIT;
ALTER TABLE `CIT`.`Ticket`
ADD PRIMARY KEY (`id`);
COMMIT;
ALTER TABLE `ERP`.`SalesQuotation`
ADD FOREIGN KEY (`sentBy`)
REFERENCES `ERP`.`Employee` (`num`);
COMMIT;
ALTER TABLE `ERP`.`SalesQuotation`
ADD FOREIGN KEY (`sentTo`)
REFERENCES `ERP`.`Customer` (`num`);
COMMIT;
ALTER TABLE `ERP`.`SalesQuotationLine`
ADD FOREIGN KEY (`contains`)
REFERENCES `ERP`.`Product` (`num`);
COMMIT;
ALTER TABLE `ERP`.`SalesQuotationLine`
ADD FOREIGN KEY (`partOf`)
REFERENCES `ERP`.`SalesQuotation` (`num`);
COMMIT;
ALTER TABLE `ERP`.`SalesOrder`
ADD FOREIGN KEY (`basedOn`)
REFERENCES `ERP`.`SalesQuotation` (`num`);
COMMIT;
ALTER TABLE `ERP`.`SalesOrder`
ADD FOREIGN KEY (`processedBy`)
REFERENCES `ERP`.`Employee` (`num`);
COMMIT;
ALTER TABLE `ERP`.`SalesOrder`
ADD FOREIGN KEY (`receivedFrom`)
REFERENCES `ERP`.`Customer` (`num`);
COMMIT;
ALTER TABLE `ERP`.`SalesOrderLine`
ADD FOREIGN KEY (`contains`)
REFERENCES `ERP`.`Product` (`num`);
COMMIT;
ALTER TABLE `ERP`.`SalesOrderLine`
ADD FOREIGN KEY (`partOf`)
REFERENCES `ERP`.`SalesOrder` (`num`);
COMMIT;
ALTER TABLE `ERP`.`SalesInvoice`
ADD FOREIGN KEY (`createdFor`)
REFERENCES `ERP`.`SalesOrder` (`num`);
COMMIT;
ALTER TABLE `ERP`.`PurchOrder`
ADD FOREIGN KEY (`placedAt`)
REFERENCES `ERP`.`Vendor` (`num`);
COMMIT;
ALTER TABLE `ERP`.`PurchOrder`
ADD FOREIGN KEY (`processedBy`)
REFERENCES `ERP`.`Employee` (`num`);
COMMIT;
ALTER TABLE `ERP`.`PurchOrder`
ADD FOREIGN KEY (`serves`)
REFERENCES `ERP`.`SalesOrder` (`num`);
COMMIT;
ALTER TABLE `ERP`.`PurchOrderLine`
ADD FOREIGN KEY (`contains`)
REFERENCES `ERP`.`Product` (`num`);
COMMIT;
ALTER TABLE `ERP`.`PurchOrderLine`
ADD FOREIGN KEY (`partOf`)
REFERENCES `ERP`.`PurchOrder` (`num`);
COMMIT;
ALTER TABLE `ERP`.`DeliveryNote`
ADD FOREIGN KEY (`contains`)
REFERENCES `ERP`.`PurchOrder` (`num`);
COMMIT;
ALTER TABLE `ERP`.`DeliveryNote`
ADD FOREIGN KEY (`operatedBy`)
REFERENCES `ERP`.`Logistics` (`num`);
COMMIT;
ALTER TABLE `ERP`.`PurchInvoice`
ADD FOREIGN KEY (`createdFor`)
REFERENCES `ERP`.`PurchOrder` (`num`);
COMMIT;
ALTER TABLE `CIT`.`Ticket`
ADD FOREIGN KEY (`allocatedTo`)
REFERENCES `CIT`.`User` (`email`);
COMMIT;
ALTER TABLE `CIT`.`Ticket`
ADD FOREIGN KEY (`createdBy`)
REFERENCES `CIT`.`User` (`email`);
COMMIT;
ALTER TABLE `CIT`.`Ticket`
ADD FOREIGN KEY (`openedBy`)
REFERENCES `CIT`.`Client` (`account`);
COMMIT;