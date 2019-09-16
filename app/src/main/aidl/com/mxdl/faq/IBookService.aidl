package com.mxdl.faq;
import com.mxdl.faq.Book;
interface IBookService {
   List<Book> getBookList();
   void addBook(in Book book);
}
