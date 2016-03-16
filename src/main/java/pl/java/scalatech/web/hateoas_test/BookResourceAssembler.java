package pl.java.scalatech.web.hateoas_test;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

public class BookResourceAssembler extends ResourceAssemblerSupport<Book, BookResource> {

    public BookResourceAssembler() {
        super(BookResourceController.class, BookResource.class);
    }

    @Override
    public BookResource toResource(Book entity) {
        BookResource bookResource = createResourceWithId(entity.getId(), entity);
        bookResource.setName(entity.getName());
        bookResource.setAuthor(entity.getAuthor());
        return bookResource;
    }

}