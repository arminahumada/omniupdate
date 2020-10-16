package com.omniupdate.addressbook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@RestController
@RequestMapping(value = "api/v1/contacts")
public class AddressBookController {

    @Autowired
    private AddressBookService addressBookService;

    /**
     * Search/List contacts with paging and optional filtering/sorting
     * Searches are CASE INSENSITIVE
     * @param filters - Filtering done via 1 or more "filter" params with ":" seperating field name and search term:
     *  "filter=firstName:bob&filter:lastName:J"
     * @param pageable
     * @return
     */
    @GetMapping
    public Page<Contact> searchContacts(
            @RequestParam(name = "filter", required = false) List<String> filters,
            @PageableDefault(size = 100)
            @SortDefault.SortDefaults(
                    @SortDefault(sort = "lastName", direction = Sort.Direction.ASC)
            ) Pageable pageable)  {

        // Atomic ref for modifying in lambda. initial spec always true
        AtomicReference<Specification<Contact>> spec = new AtomicReference<>(Specification.where((root, query, cb) ->
                cb.isTrue(cb.literal(true))));

        if(!CollectionUtils.isEmpty(filters)) {
            filters.stream()
                    .map(filter -> filter.split(":", 2))
                    .forEach(filter -> {
                        spec.set(spec.get().and(new CustomerFilterSpec(filter[0], filter[1])));
                    });
        }

        return addressBookService.search(spec.get(), pageable);
    }

    @GetMapping(path = "/{id}")
    public Contact getContact(@PathVariable(name = "id") Long id) {
        return addressBookService.getContact(id);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Contact createContact(@Valid @RequestBody Contact contact) {
        Assert.isNull(contact.getId(), "Contact create should not provide id");
        return addressBookService.createContact(contact);
    }

    @PutMapping(path = "/{id}")
    public Contact updateContact(@PathVariable(name = "id") Long id,
                                 @Valid @RequestBody Contact contact)  {
        if(contact.getId() != null) {
            Assert.isTrue(id.equals(contact.getId()), "id in payload is not equal to path id");
        }

        contact.setId(id);
        return addressBookService.updateContact(contact);
    }

    @DeleteMapping(path = "/{id}")
    public void deleteContact(@PathVariable(name = "id") Long id) {
        addressBookService.deleteContact(id);
    }
}
