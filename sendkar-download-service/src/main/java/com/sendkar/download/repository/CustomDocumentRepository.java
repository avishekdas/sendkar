package com.sendkar.download.repository;

import com.sendkar.download.model.Document;
import com.sendkar.download.payload.DocSearchRequest;

import java.util.List;

public interface CustomDocumentRepository {
    public List<Document> searchDocument(DocSearchRequest searchRequest);
}

