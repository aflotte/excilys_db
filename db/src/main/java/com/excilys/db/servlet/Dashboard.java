package com.excilys.db.servlet;

import java.text.MessageFormat;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.excilys.db.page.IPageComputerDTO;

/**
 * Servlet implementation class GetComputer.
 */

@Controller
@RequestMapping(value = "/dashboard")
public class Dashboard {
    static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(Dashboard.class);
    private static final String ORDER_BY = "orderBy";
    private static final String ACTUAL_PAGE = "actualPage";
    private static final String PAGE_SIZE = "pageSize";
    private static final String SEARCH = "search";
    private static final String SORT_PATH = "sortPath";
    private static final String DEFAULT_PAGE = "1";
    private static final String DEFAULT_PAGE_SIZE = "10";
    private static final String DEFAULT_SEARCH = "";
    private static final String DEFAULT_SORT = "computer.id";
    private static final String DEFAULT_ORDER = "asc";

    @Autowired
    private IPageComputerDTO pageComputer;

    public Dashboard(IPageComputerDTO pageComputer) {
        this.pageComputer = pageComputer;
    }

    @GetMapping
    public ModelAndView handleGet(@RequestParam(value = ACTUAL_PAGE, defaultValue = DEFAULT_PAGE) int actualPage,
            @RequestParam(value = PAGE_SIZE, defaultValue = DEFAULT_PAGE_SIZE) int sizePage,
            @RequestParam(value = SEARCH, defaultValue = DEFAULT_SEARCH) String search,
            @RequestParam(value = "sort", defaultValue = DEFAULT_SORT) String toSort,
            @RequestParam(value = ORDER_BY, defaultValue = DEFAULT_ORDER) String orderBy) {
        ModelAndView modelAndView = new ModelAndView("index");
        String[] preparedSort = prepareSort(toSort,orderBy,modelAndView);
        toSort = preparedSort[0];
        orderBy = preparedSort[1];
        if (sizePage < 10) {
            sizePage = 10;
        }
        String searchJSP;
        if (search.equals("")) {
            searchJSP = "";
            pageComputer.init(actualPage, sizePage);
        }else {
            searchJSP = "&search=" + search;
            pageComputer.init(actualPage, sizePage, search);
        }
        pageComputer.setSortBy(toSort);
        pageComputer.setOrderBy(orderBy);
        modelAndView.addObject(SEARCH, searchJSP);
        modelAndView.addObject("page", pageComputer);
        return modelAndView;
    }

    @PostMapping
    public ModelAndView handlePost() {
        ModelAndView modelAndView = new ModelAndView("index");
        return modelAndView;
    }

    /**
     * @param orderBy 
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    /*
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String[] preparedSort = prepareSort(request);
            String toSort = preparedSort[0];
            String orderBy = preparedSort[1];
            int sizePage;

            pageComputer.setSortBy(toSort);
            pageComputer.setOrderBy(orderBy);
            request.setAttribute(SEARCH, searchJSP);
            request.setAttribute("page", pageComputer);
            RequestDispatcher rd =
                    request.getRequestDispatcher("/WEB-INF/index.jsp");
            rd.forward(request, response);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     *
     */
    /*
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            doGet(request, response);
        } catch (Exception e) {
            logger.debug(e.getMessage());
        }
    }

    /**
     *
     * @param request la requete
     * @param tested la colonne servant pour sort
     * @return la string de l'url
     */
    private String casePrepareSort(ModelAndView modelAndView, String tested, String orderBy) {
        String preparation = MessageFormat.format("&sort={0}", tested);
        if (orderBy.equals("asc")) {
            modelAndView.addObject("computerPath", MessageFormat.format("&sort={0}&orderBy=desc", tested));
        }
        return preparation;
    }

    /**
     *
     * @param modelAndView 
     * @param request la requete
     * @return { Sort, OrderBy }
     */
    private String[] prepareSort(String toSort, String orderBy, ModelAndView modelAndView) {
        String preparation = "";
        modelAndView.addObject("computerPath", "&sort=computer&orderBy=asc");
        modelAndView.addObject("introducedPath", "&sort=introduced&orderBy=asc");
        modelAndView.addObject("discontinuedPath", "&sort=discontinued&orderBy=asc");
        modelAndView.addObject("companyPath", "&sort=company&orderBy=asc");
        modelAndView.addObject(SORT_PATH, "");
        if (!toSort.equals(DEFAULT_SORT)) {
            switch (toSort) {
            case "computer":
                preparation = casePrepareSort(modelAndView, "computer", orderBy);
                toSort = "computer.name";
                break;
            case "introduced":
                preparation = casePrepareSort(modelAndView, "introduced", orderBy);
                toSort = "computer.introduced";
                break;
            case "discontinued":
                preparation = casePrepareSort(modelAndView, "discontinued", orderBy);
                toSort = "computer.discontinued";
                break;
            case "company":
                preparation = casePrepareSort(modelAndView, "company", orderBy);
                toSort = "company.name";
                break;
            default:
                toSort = DEFAULT_SORT;
                break;
            }
        }
        if (!preparation.isEmpty()) {
            if (orderBy.equals("desc")) {
                preparation += "&orderBy=desc";
                orderBy = "desc";
            } else {
                preparation += "&orderBy=asc";
                orderBy = "asc";
            }
        } else {
            orderBy = "asc";
        }

        modelAndView.addObject(SORT_PATH, preparation);
        return new String[]{toSort, orderBy};
    }


}
