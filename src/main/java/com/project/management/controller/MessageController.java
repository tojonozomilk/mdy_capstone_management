package com.project.management.controller;

import com.project.management.entity.Message;
import com.project.management.service.MessageService;
import com.project.management.pojo.Result;
import com.project.management.pojo.PageData;
import com.project.management.pojo.MessageQuery;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @description Message API Controller
 * @author Ethan
 * @date 2024-07-08 20:46:21
 */
@RestController
@RequestMapping("/message")
public class MessageController {

    @Autowired
    private MessageService service;

	/**
	 * Get all messages paginated
	 *
	 * @param query condition
	 * @return PageData
	 */
    @GetMapping
    public Result<PageData<Message>> getAll(MessageQuery query) {
        PageData<Message> result = service.getAll(query);
        return Result.success(result);
    }

	/**
	 * Get message by id
	 *
	 * @param id ID
	 * @return Message object
	 */
    @GetMapping("{id}")
    public Result<Message> get(@PathVariable Integer id) {
        Message message = service.get(id);
        return Result.success(message);
    }

	/**
	 * Upsert message
	 *
	 * @param message Message in req body
	 */
    @PostMapping
    public Result<Message> save(@RequestBody Message message) {
        service.saveMessage(message);
        return Result.success();
    }

	/**
	 * Delete Message
	 *
	 * @param id ID
	 */
    @DeleteMapping("{id}")
    public Result<Message> delete(@PathVariable Integer id) {
        service.delete(id);
        return Result.success();
    }
}