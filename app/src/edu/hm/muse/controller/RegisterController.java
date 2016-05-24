/*
 * **
 *  *                                        __          ____                                     __
 *  *     /'\_/`\                 __        /\ \        /\  _`\                                __/\ \__
 *  *    /\      \  __  __   ___ /\_\    ___\ \ \___    \ \,\L\_\     __    ___  __  __  _ __ /\_\ \ ,_\  __  __
 *  *    \ \ \__\ \/\ \/\ \/' _ `\/\ \  /'___\ \  _ `\   \/_\__ \   /'__`\ /'___\\ \/\ \/\`'__\/\ \ \ \/ /\ \/\ \
 *  *     \ \ \_/\ \ \ \_\ \\ \/\ \ \ \/\ \__/\ \ \ \ \    /\ \L\ \/\  __//\ \__/ \ \_\ \ \ \/ \ \ \ \ \_\ \ \_\ \
 *  *      \ \_\\ \_\ \____/ \_\ \_\ \_\ \____\\ \_\ \_\   \ `\____\ \____\ \____\ \____/\ \_\  \ \_\ \__\\/`____ \
 *  *       \/_/ \/_/\/___/ \/_/\/_/\/_/\/____/ \/_/\/_/    \/_____/\/____/\/____/\/___/  \/_/   \/_/\/__/ `/___/> \
 *  *                                                                                                         /\___/
 *  *                                                                                                         \/__/
 *  *
 *  *     ____                                               __          ____
 *  *    /\  _`\                                            /\ \        /\  _`\
 *  *    \ \ \L\ \     __    ____    __     __     _ __  ___\ \ \___    \ \ \L\_\  _ __  ___   __  __  _____
 *  *     \ \ ,  /   /'__`\ /',__\ /'__`\ /'__`\  /\`'__\'___\ \  _ `\   \ \ \L_L /\`'__\ __`\/\ \/\ \/\ '__`\
 *  *      \ \ \\ \ /\  __//\__, `\\  __//\ \L\.\_\ \ \/\ \__/\ \ \ \ \   \ \ \/, \ \ \/\ \L\ \ \ \_\ \ \ \L\ \
 *  *       \ \_\ \_\ \____\/\____/ \____\ \__/.\_\\ \_\ \____\\ \_\ \_\   \ \____/\ \_\ \____/\ \____/\ \ ,__/
 *  *        \/_/\/ /\/____/\/___/ \/____/\/__/\/_/ \/_/\/____/ \/_/\/_/    \/___/  \/_/\/___/  \/___/  \ \ \/
 *  *                                                                                                    \ \_\
 *  *    This file is part of BREW.
 *  *
 *  *    BREW is free software: you can redistribute it and/or modify
 *  *    it under the terms of the GNU General Public License as published by
 *  *    the Free Software Foundation, either version 3 of the License, or
 *  *    (at your option) any later version.
 *  *
 *  *    BREW is distributed in the hope that it will be useful,
 *  *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  *    GNU General Public License for more details.
 *  *
 *  *    You should have received a copy of the GNU General Public License
 *  *    along with BREW.  If not, see <http://www.gnu.org/licenses/>.                                                                                                  \/_/
 *
 */

package edu.hm.muse.controller;

import edu.hm.muse.exception.SuperFatalAndReallyAnnoyingException;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Controller
public class RegisterController {

    private JdbcTemplate jdbcTemplate;

    @Resource(name = "dataSource")
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @RequestMapping(value = "/register.secu", method = RequestMethod.GET)
    public ModelAndView showLoginScreen() {
        ModelAndView mv = new ModelAndView("register");
        //Show the Msg
        mv.addObject("msg", "Neuen Benutzer anlegen. Bitte Usernamen und Passwort eintragen.");
        return mv;
    }

    @RequestMapping(value = "/register.secu", method = RequestMethod.POST)
    public ModelAndView doSomeRegister(@RequestParam(value = "new_uname", required = false) String new_uname, @RequestParam(value = "new_mpwd", required = false) String new_mpwd, HttpSession session) {
    	if (null == new_uname || null == new_mpwd || new_uname.isEmpty() || new_mpwd.isEmpty()) {
            throw new SuperFatalAndReallyAnnoyingException("I can not process, because the requestparam new_uname or new_mpwd is empty or null or something like this");
        }

        if (isLoginNameTaken(new_uname)) {
            ModelAndView mv = new ModelAndView("register");
            mv.addObject("msg", "duuuh! please try another login name!!");
            return mv;
        }

        //Select the Last ID from the Table
    	String sqlSelect = "SELECT id FROM M_USER ORDER BY id DESC LIMIT 1";
        int lastId = jdbcTemplate.queryForInt(sqlSelect);
        //Increment the last ID
        lastId++;

        String hpwd = hashen256(new_mpwd);

        //Build the query with the new User and Passwd
        String sqlInsert = String.format("insert into M_USER (ID,muname,mpwd) values (%s,'%s','%s')", lastId, new_uname, hpwd);

        int res = 0;
        try {
        	//execute the query and check exceptions
            res = jdbcTemplate.update(sqlInsert);
        } catch (DataAccessException e) {
            throw new SuperFatalAndReallyAnnoyingException(String.format("Sorry but %sis a bad grammar or has following problem %s", sqlInsert, e.getMessage()));
        }

        //Register Ok
        //Do Autologin
        if (res > 0) {
            if (res > 0) {
                session.setAttribute("login", true);
                session.setAttribute("user", new_uname);
                return new ModelAndView("redirect:intern.secu");
            }
        }
        //Error
        return returnToRegister(session);
    }

    private ModelAndView returnToRegister(HttpSession session) {
        //Ohhhhh not correct try again
        return new ModelAndView("redirect:register.secu");
    }

    private String hashen256(String mpwd) {
        String hpwd = null;

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            digest.update(mpwd.getBytes(), 0, mpwd.length());

            hpwd = new BigInteger(1, digest.digest()).toString(16);
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return hpwd;
    }

    private boolean isUserInputValid(String mname) {
        return mname.matches("[A-Za-z0-9]+");
    }

    private boolean isLoginNameTaken(String mname) {
        String sql = String.format("select count(*) from M_USER where muname = '%s'", mname);
        int res=0;
        try {
            res = jdbcTemplate.queryForInt(sql);
            if (res > 0) {
                return true;
            }
        } catch (DataAccessException e) {
            throw new SuperFatalAndReallyAnnoyingException(String.format("Sorry but %sis a bad grammar or has following problem %s", sql, e.getMessage()));
        }
        return false;
    }

}
