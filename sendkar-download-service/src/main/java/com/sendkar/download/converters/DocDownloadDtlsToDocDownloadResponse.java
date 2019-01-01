package com.sendkar.download.converters;

import com.sendkar.download.model.DocDownloadDtls;
import com.sendkar.download.payload.DocDownloadResponse;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class DocDownloadDtlsToDocDownloadResponse implements Converter<DocDownloadDtls, DocDownloadResponse> {

    @Override
    public DocDownloadResponse convert(DocDownloadDtls documentDownload) {
        DocDownloadResponse docDownloadResponse = new DocDownloadResponse(
                documentDownload.getId(), documentDownload.getUsername());
        return docDownloadResponse;
    }
}
