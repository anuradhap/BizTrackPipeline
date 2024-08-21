package com.bizTrack.BizTrack.Repository;

import com.bizTrack.BizTrack.Model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
}
