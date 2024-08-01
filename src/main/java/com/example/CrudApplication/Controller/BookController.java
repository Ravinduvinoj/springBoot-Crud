package com.example.CrudApplication.Controller;

import com.example.CrudApplication.model.Book;
import com.example.CrudApplication.repo.BookRepo;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api")
@Data
public class BookController {

    private final BookRepo bookRepo;

    @Autowired
    public BookController(BookRepo bookRepo) {
        this.bookRepo = bookRepo;
    }

    //getting all books
    @GetMapping("/getAllBooks")
    public List<Book> getAllBooks() {
        return bookRepo.findAll();
    }
//    public ResponseEntity<List<Book>> getAllBooks(){
//        try{
//    List<Book> bookList = new ArrayList<>();
//    bookRepo.findAll().forEach(bookList::add);
//
//    if(bookList.isEmpty()){
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }
//        }catch(Exception ex){
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//
//        return new ResponseEntity<>(HttpStatus.OK);
//    }

    @GetMapping("/getBookById/{id}")
    public ResponseEntity<Book> getBookByID(@PathVariable Long id){
        Optional<Book> bookData= bookRepo.findById(id);

        if(bookData.isPresent()){
           return new ResponseEntity<>(bookData.get() , HttpStatus.OK);
        }
        return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/addBook")
    public void addBook(@RequestBody Book book){
        Book bookObj = bookRepo.save(book);
        new ResponseEntity<>(bookObj, HttpStatus.OK);
    }

    @PutMapping("/updateBookById/{id}")
    public Book updateBook(@PathVariable Long id, @RequestBody Book updatedBook) {
        Optional<Book> optionalBook = bookRepo.findById(id);
        if (!optionalBook.isPresent()) {
            throw new IllegalArgumentException("Book not found");
        }

        Book book = optionalBook.get();
        book.setTitle(updatedBook.getTitle());
        book.setAuthor(updatedBook.getAuthor());

        return bookRepo.save(book);
    }

//    @PutMapping("/updateBookById/{id}")
//    public ResponseEntity<Book> updateBookById(@PathVariable Long id, @RequestBody Book newBookData) {
//        Optional<Book> oldBookData = bookRepo.findById(id);
//
//        if (oldBookData.isPresent()) {
//
//            Book updatedBookData = new Book();
//            updatedBookData.setAuthor(newBookData.getAuthor());
//            updatedBookData.setTitle(newBookData.getTitle());
//            Book bookObj = bookRepo.save(updatedBookData);
//            return new ResponseEntity<>(bookObj, HttpStatus.OK);
//
//        }
//        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }
    @DeleteMapping("/DeleteBookById/{id}")
    public ResponseEntity<HttpStatus> deleteBookById(@PathVariable Long id){
    bookRepo.deleteById(id);
    return  new ResponseEntity<>(HttpStatus.OK);
    }

}
