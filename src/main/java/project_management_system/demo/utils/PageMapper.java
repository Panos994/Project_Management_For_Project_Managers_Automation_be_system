package project_management_system.demo.utils;

import org.springframework.data.domain.Page;
import project_management_system.demo.dto.PageResponseDTO;

public final class PageMapper {

    public static <T>PageResponseDTO<T> toResponse(Page<T> page){
        return PageResponseDTO.<T>builder()
                .items(page.getContent())
                .page(page.getNumber())
                .size(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .hasNext(page.hasNext())
                .hasPrevious(page.hasPrevious())
                .build();
    }
}
