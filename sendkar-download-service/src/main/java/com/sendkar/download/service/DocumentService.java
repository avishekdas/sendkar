package com.sendkar.download.service;

import com.sendkar.download.converters.DocumentToDocumentResponse;
import com.sendkar.download.exception.ResourceNotFoundException;
import com.sendkar.download.model.Document;
import com.sendkar.download.payload.ApiResponse;
import com.sendkar.download.payload.DocSearchRequest;
import com.sendkar.download.payload.DocumentResponse;
import com.sendkar.download.repository.CustomDocumentRepository;
import com.sendkar.download.repository.DocDownloadDtlsRepository;
import com.sendkar.download.repository.DocumentRepository;
import com.sendkar.download.repository.UserRepository;
import com.sendkar.download.service.aws.SNSServices;
import com.sendkar.download.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class DocumentService {

    private static final Logger logger = LoggerFactory.getLogger(DocumentService.class);

    @Autowired
    DocumentRepository documentRepository;

    @Autowired
    DocDownloadDtlsRepository docDownloadDtlsRepo;

    @Autowired
    CustomDocumentRepository customDocRepository;

    @Autowired
    private DocumentToDocumentResponse docToDocResponseConvertor;

    @Transactional
    public DocumentResponse getDocumentDtls(Long docId){
        DocumentResponse docResponse = new DocumentResponse();
        if(docId != null) {
            Document document = documentRepository.findById(docId)
                    .orElseThrow(() ->
                            new ResourceNotFoundException("Document", "Document Id", docId)
                    );
            docResponse = docToDocResponseConvertor.convert(document);
        } else {
            new ResourceNotFoundException("Document", "docId", docId);
        }
        return docResponse;
    }

    @Transactional
    public List<DocumentResponse> searchDocument(DocSearchRequest searchRequest){
        List<DocumentResponse> docResponseList = new ArrayList<DocumentResponse>();
        DocumentResponse docResponse = null;
        List<Document> documents = customDocRepository.searchDocument(searchRequest);
        for(Document doc : documents) {
            docResponse = docToDocResponseConvertor.convert(doc);
            docResponse.setDocDownloadDtlsList(docDownloadDtlsRepo.findByDocid(docResponse.getId()));
            docResponseList.add(docResponse);
        }
        return docResponseList;
    }
}
