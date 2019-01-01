package com.sendkar.download.repository;

import com.sendkar.download.model.Document;
import com.sendkar.download.payload.DocSearchRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CustomDocumentRepositoryImpl implements CustomDocumentRepository {

    private static final String SEARCH_DOCUMENT = "select * from document";

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    CustomDocumentRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Document> searchDocument(DocSearchRequest searchRequest) {

        List<Document> docList = new ArrayList<Document>();
        if(searchRequest == null) {
            return docList;
        }
        StringBuffer strBuff = new StringBuffer();
        strBuff.append(SEARCH_DOCUMENT);
        List<String> criterias = new ArrayList<String>();

        if(searchRequest.getFilename() != null) {
            criterias.add(" filename = '" + searchRequest.getFilename() + "' ");
        }
        if(searchRequest.getId() != null) {
            criterias.add(" id = " + searchRequest.getId() + " ");
        }
        if(searchRequest.getMessage() != null) {
            criterias.add(" message = '" + searchRequest.getMessage() + "' ");
        }
        if(searchRequest.getReceiveraddress() != null) {
            criterias.add(" receiveraddress = '" + searchRequest.getReceiveraddress() + "' ");
        }
        if(searchRequest.getReceivermobilenumber() != null) {
            criterias.add(" receivermobilenumber = " + searchRequest.getReceivermobilenumber() + " ");
        }
        if(searchRequest.getSenderaddress() != null) {
            criterias.add(" senderaddress = '" + searchRequest.getSenderaddress() + "' ");
        }
        if(searchRequest.getSendermobilenumber() != null) {
            criterias.add(" sendermobilenumber = " + searchRequest.getSendermobilenumber() + " ");
        }
        if(searchRequest.getUploadername() != null) {
            criterias.add(" uploadername = '" + searchRequest.getUploadername() + "' ");
        }

        if(criterias.size() > 0) {
            strBuff.append(" where ");
            for(int i=0; i < criterias.size(); i++){
                strBuff.append(criterias.get(i));
                if(i + 1 < criterias.size()) {
                    strBuff.append(" and ");
                }
            }
        }

        docList = jdbcTemplate.query(strBuff.toString(),
                new BeanPropertyRowMapper<>(Document.class)
        );

        return docList;
    }
}

