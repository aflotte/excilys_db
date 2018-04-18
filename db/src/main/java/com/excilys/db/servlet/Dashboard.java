package com.excilys.db.servlet;

import java.io.IOException;
import java.text.MessageFormat;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
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

    private IPageComputerDTO pageComputer;
    
    public Dashboard(IPageComputerDTO pageComputer) {
        this.pageComputer = pageComputer;
    }
    
    @GetMapping
    public ModelAndView handleGet(@RequestParam(value = "pageNumber", defaultValue = DEFAULT_PAGE) int pageNumber) {
        return handlePost();
    }
    
    @PostMapping
    public ModelAndView handlePost() {
        ModelAndView modelAndView = new ModelAndView("index");
        return modelAndView;
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    /*
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int actualPage;
            String[] preparedSort = prepareSort(request);
            String toSort = preparedSort[0];
            String orderBy = preparedSort[1];
            if (request.getParameter(ACTUAL_PAGE) == null) {
                actualPage = 1;
            } else {
                if (request.getParameter(ACTUAL_PAGE).isEmpty()) {
                    actualPage = 1;
                } else {
                    actualPage = Integer.parseInt(request.getParameter(ACTUAL_PAGE));
                }
            }
            int sizePage;
            if ((request.getParameter(PAGE_SIZE) == null) || (request.getParameter(PAGE_SIZE).isEmpty())) {
                sizePage = 10;
            } else {
                sizePage = Integer.parseInt(request.getParameter(PAGE_SIZE));
            }
            if (sizePage < 10) {
                sizePage = 10;
            }
            String search;
            String searchJSP;
            if ((request.getParameter(SEARCH) == null) || (request.getParameter(SEARCH).isEmpty())) {
                searchJSP = "";
                pageComputer.init(actualPage, sizePage);
            } else {
                String debug = "enter in search not empty";
                logger.debug(debug);
                search = request.getParameter(SEARCH);
                searchJSP = "&search=" + search;
                pageComputer.init(actualPage, sizePage, search);
            }
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
    private String casePrepareSort(HttpServletRequest request, String tested) {
        String preparation = MessageFormat.format("&sort={0}", tested);
        if ((request.getParameter(ORDER_BY) != null) && (request.getParameter(ORDER_BY).equals("asc"))) {
            request.setAttribute("computerPath", MessageFormat.format("&sort={0}&orderBy=desc", tested));
        }
        return preparation;
    }

    /**
     *
     * @param request la requete
     * @return { Sort, OrderBy }
     */
    private String[] prepareSort(HttpServletRequest request) {
        String toSort = "";
        String preparation = "";
        request.setAttribute("computerPath", "&sort=computer&orderBy=asc");
        request.setAttribute("introducedPath", "&sort=introduced&orderBy=asc");
        request.setAttribute("discontinuedPath", "&sort=discontinued&orderBy=asc");
        request.setAttribute("companyPath", "&sort=company&orderBy=asc");
        request.setAttribute(SORT_PATH, "");
        if (!((request.getParameter("sort") == null) || (request.getParameter("sort").isEmpty()))) {
            toSort = request.getParameter("sort");
            request.setAttribute(SORT_PATH, "");
            switch (toSort) {
            case "computer":
                preparation = casePrepareSort(request, "computer");
                toSort = "computer.name";
                break;
            case "introduced":
                preparation = casePrepareSort(request, "introduced");
                toSort = "computer.introduced";
                break;
            case "discontinued":
                preparation = casePrepareSort(request, "discontinued");
                toSort = "computer.discontinued";
                break;
            case "company":
                preparation = casePrepareSort(request, "company");
                toSort = "company.name";
                break;
            default:
                toSort = "computer.id";
                break;
            }
        } else {
            toSort = "computer.id";
        }
        String orderBy;
        if (!((request.getParameter(ORDER_BY) == null) || (request.getParameter(ORDER_BY).isEmpty()))) {
            if (!preparation.isEmpty()) {
                if (request.getParameter(ORDER_BY).equals("desc")) {
                    preparation += "&orderBy=desc";
                    orderBy = "desc";
                } else {
                    preparation += "&orderBy=asc";
                    orderBy = "asc";
                }
            } else {
                orderBy = "asc";
            }
        } else {
            orderBy = "asc";
        }

        request.setAttribute(SORT_PATH, preparation);
        return new String[]{toSort, orderBy};
    }


}
