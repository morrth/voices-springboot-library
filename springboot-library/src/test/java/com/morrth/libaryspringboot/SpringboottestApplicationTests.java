//package com.morrth.libaryspringboot;
//
//import com.morrth.libaryspringboot.repository.BookRepository;
//import com.morrth.libaryspringboot.util.MSTTSSpeechUtil;
//import com.morrth.libaryspringboot.entity.Book;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//
////静态导包
//@SpringBootTest
//class SpringboottestApplicationTests {
//@GetMapping("/find/{page}/{size}")
//    public Page<Book> find(@PathVariable("page") Integer page, @PathVariable("size") Integer size, @RequestParam(value = "select", defaultValue = "") String select,@RequestParam(value = "selectk", defaultValue = "") String selectk, @RequestParam(value = "sortname", defaultValue = "") String sortname, @RequestParam(value = "sortorder") int sortorder) {
//        //分页排序部分
//        Sort sort = Sort.by("id").ascending();
//        if (!sortname.isEmpty()){
//            if (sortorder==1)sort = Sort.by(sortname).ascending();
//            else if(sortorder==0) sort = Sort.by(sortname).descending();
//        }
//        PageRequest request = PageRequest.of(page, size, sort);
////        bookRepository.findAllByioaon("1",request);
//        //表示创建一个空的条件匹配器（初始化）（进行模糊查询或等其它查询配置）
//        ExampleMatcher exampleMatcher = ExampleMatcher.matching();
////        ExampleMatcher Matcher = ExampleMatcher.matching().withMatcher("",ExampleMatcher.GenericPropertyMatchers.contains());
////        ExampleMatcher.GenericPropertyMatchers.contains() 包含关键字
////        ExampleMatcher.GenericPropertyMatchers.startsWith() 前缀匹配
////        ExampleMatcher.GenericPropertyMatchers.ignoreCase() 忽略大小写
//        //条件筛选部分
//        if (!select.isEmpty() || !selectk.isEmpty()) {
//            Book book = new Book();
//            if (!select.isEmpty()){
//                book.setName(select);
//                exampleMatcher=ExampleMatcher.matching().withMatcher("name", ExampleMatcher.GenericPropertyMatcher::contains);
//                //将匹配对象封装成Example对象
//                if (!bookRepository.exists(Example.of(book,exampleMatcher))) {
//                    book.setName(null);
//                    book.setAuthor(select);
//                    exampleMatcher=ExampleMatcher.matching().withMatcher("author", ExampleMatcher.GenericPropertyMatcher::contains);
//                    if (!bookRepository.exists(Example.of(book,exampleMatcher))&&select.matches("\\d{1,12}")) {
//                        book.setAuthor(null);
//                        book.setId(select);
//                        exampleMatcher=ExampleMatcher.matching().withMatcher("id", ExampleMatcher.GenericPropertyMatcher::contains);
//                        if (!bookRepository.exists(Example.of(book,exampleMatcher))){
//                            book.setId(null);
//                        }
//                    }else{
//
//                    }
//                }
//            }
//            if (!selectk.isEmpty()){
//                Book b2 = new Book();
//                b2.setBookcase(selectk);
//                if (!bookRepository.exists(Example.of(b2))){
//                    book.setPublish(selectk);
//                }else {
//                    book.setBookcase(selectk);
//                }
//            }
//            return bookRepository.findAll(Example.of(book,exampleMatcher), request);
//        } else {
//            return bookRepository.findAll(request);
//        }
//
//    }
//    @Autowired
//    private BookRepository repository;
//
//    @Test
//    void contextLoads() {
//        PageRequest pageRequest = PageRequest.of(0,6);
//        Page<Book> page = repository.findAll(pageRequest);
//        int i = 0;
//    }
//
//    @Test
//    void save(){
//        Book book = new Book();
//        book.setName("Spring Boot");
//        book.setAuthor("张三");
//        Book book1 = repository.save(book);
//        System.out.println(book1);
//    }
//
//    @Test
//    void findById(){
//        Book book = repository.findById(1).get();
//        System.out.println(book);
//    }
//
//    @Test
//    void update(){
//        Book book = new Book();
//        book.setId(117);
//        book.setName("测试测试");
//        Book book1 = repository.save(book);
//        System.out.println(book1);
//    }
//
//    @Test
//    void delete(){
//        repository.deleteById(117);
//    }
//    @Test
//    void r(){
//        MSTTSSpeechUtil.creatSpeech();
////        MSTTSSpeechUtil.speak("欢迎使用po语音助手,请问有什么能帮忙的吗");
//        for (String s:MSTTSSpeechUtil.getVoices())
//        System.out.println(s);//输出语音库
//    }
//}
