package uz.jamshid.app_warehouse.service;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import uz.jamshid.app_warehouse.entity.Attachment;
import uz.jamshid.app_warehouse.entity.AttachmentContent;
import uz.jamshid.app_warehouse.payload.Result;
import uz.jamshid.app_warehouse.repository.AttachmentContentRepository;
import uz.jamshid.app_warehouse.repository.AttachmentRepository;

import java.util.Iterator;

@Service
public class AttachmentService {
    @Autowired
    AttachmentRepository attachmentRepository;
    @Autowired
    AttachmentContentRepository attachmentContentRepository;

    @SneakyThrows
    public Result upload(MultipartHttpServletRequest request) {
        Iterator<String> fileNames = request.getFileNames();
        MultipartFile file = request.getFile(fileNames.next());

        Attachment attachment = new Attachment(null, file.getOriginalFilename(), file.getSize(), file.getContentType());
        Attachment savedAttachment = attachmentRepository.save(attachment);

        AttachmentContent attachmentContent = new AttachmentContent(null, file.getBytes(), savedAttachment);
        attachmentContentRepository.save(attachmentContent);

        return new Result("File uploaded", true, savedAttachment.getId());
    }
}
