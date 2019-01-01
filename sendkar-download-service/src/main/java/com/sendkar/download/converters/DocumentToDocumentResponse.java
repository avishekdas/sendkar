package com.sendkar.download.converters;

import com.sendkar.download.model.Document;
import com.sendkar.download.payload.DocumentResponse;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class DocumentToDocumentResponse implements Converter<Document, DocumentResponse> {

    @Override
    public DocumentResponse convert(Document document) {
        DocumentResponse docResponse = new DocumentResponse(
                document.getId(), document.getUploadername(), document.getFilename(), document.getOtp(),
                document.getSendermobilenumber(), document.getReceivermobilenumber(),
                document.getSenderaddress(), document.getReceiveraddress(), document.getMessage(),
                document.getPagecount(),document.getDownloadcount()
        );
        return docResponse;
    }
}
