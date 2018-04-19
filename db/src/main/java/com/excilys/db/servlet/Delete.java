package com.excilys.db.servlet;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.excilys.db.service.IComputerService;

@Controller
public class Delete {
    static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(AddComputer.class);
    private static final String SELECTION = "selection";
    private static final String DEFAULT_VALUE = "";
    
    @Autowired
    private IComputerService computerService;

    public Delete(IComputerService computerService) {
        this.computerService = computerService;
    }
    
    @GetMapping("/delete")
    public ModelAndView handleGet(@RequestParam(value = SELECTION, defaultValue = DEFAULT_VALUE) String toDeleteString) {
        logger.warn(toDeleteString);
        if (!toDeleteString.equals(DEFAULT_VALUE)) {
            String[] toDelete = toDeleteString.split(",");
            List<Integer> toDeleteId = new ArrayList<>();
            for (int i = 0; i < toDelete.length; i++) {
                logger.warn(toDelete[i]);
                try {
                    toDeleteId.add(Integer.parseInt(toDelete[i]));
                } catch (Exception e) {
                    logger.debug(e.getMessage());
                }
            }
            computerService.deleteListComputer(toDeleteId);
        }
        return new ModelAndView("redirect:/dashboard");
    }
    
    @PostMapping("/delete")
    public ModelAndView handlePost(@RequestParam(value = SELECTION, defaultValue = DEFAULT_VALUE) String toDeleteString) {
        return handleGet(toDeleteString);
    }
}
