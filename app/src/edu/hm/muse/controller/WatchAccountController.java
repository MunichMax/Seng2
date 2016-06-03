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

import edu.hm.muse.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.sql.Types;


@Controller
public class WatchAccountController extends functions {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    SaltErstellen saltErstellen;

    @Resource(name = "dataSource")
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @RequestMapping(value = "/internchange.secu", method = RequestMethod.GET)
    public ModelAndView showAccountToChange(HttpSession session) {
        if ((null == session) || (null == session.getAttribute("login")) || ((Boolean) session.getAttribute("login") == false)) {
            return new ModelAndView("redirect:login.secu");
        }
        String uname = (String) session.getAttribute("user");

        String sql = "select ID, muname,mpwd from M_USER where muname = ?";

        User u = jdbcTemplate.queryForObject(sql, new Object[]{uname}, new int[]{Types.VARCHAR}, new UserMapper());

        ModelAndView mv = new ModelAndView("internchange");
        mv.addObject("userDomain", u);

        return mv;
    }



    @RequestMapping(value = "/intern.secu", method = RequestMethod.GET)
    public ModelAndView showAccount(HttpSession session) {
        if ((null == session) || (null == session.getAttribute("login")) || ((Boolean) session.getAttribute("login") == false)) {
            return new ModelAndView("redirect:login.secu");
        }
        String uname = (String) session.getAttribute("user");

        String sql = "select ID, muname,mpwd from M_USER where muname = ?";

        User u = jdbcTemplate.queryForObject(sql, new Object[]{uname}, new int[]{Types.VARCHAR}, new UserMapper());

        ModelAndView mv = new ModelAndView("intern");
        mv.addObject("userDomain", u);
        String pwd;
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
//            digest.reset();
            digest.update(u.getUpwd().getBytes());
            byte[] md = digest.digest();
            StringBuffer hex = new StringBuffer();
            for (int i = 0; i < md.length; i++) {
                if(md[i] <= 15 && md[i] >= 0){
                    hex.append("0");
                }
                hex.append(Integer.toHexString(0xFF & md[i]));
            }
            pwd = hex.toString();
        } catch (Exception e) {
            //This is a frog not a bug...perhaps your system do not want this.....
            pwd = "7c6a180b36896a0a8c02787eeafb0e4c"; //With this you can try it.....
        }
        
        mv.addObject("md5pwd", pwd);
        
        return mv;
    }
    

    @RequestMapping(value = "/change.secu", method = RequestMethod.POST)
    public ModelAndView changeAccount(HttpSession session, @RequestParam(value = "uid", required = true) String uid, @RequestParam(value = "uname", required = true) String uname, @RequestParam(value = "upwd", required = true) String upwd) {
        if ((null == session) || (null == session.getAttribute("login")) || (!((Boolean) session.getAttribute("login")))) {
            return new ModelAndView("redirect:login.secu");
        }


        String getSalt = "select salt from M_USER where muname = ?";
        String salt = jdbcTemplate.queryForObject(getSalt, new Object[]{uname}, String.class);

        //String getSalt = String.format("select salt from M_USER where muname = '%s'", uname);
        //String salt = jdbcTemplate.queryForObject(getSalt, String.class);

        StringBuilder saltedPw = new StringBuilder();
        saltedPw.append(salt);
        saltedPw.append(upwd);


        String hpwd = hashen256(saltedPw.toString());

        String sql = "update M_USER set  mpwd = ? " +
                "where " +
                "ID = "+uid;

        //Todo: Passwort mit Salt versehen
        //Die Fragezeichen sind für des jdbcTemplate Zeile in dem Object Array

        jdbcTemplate.update(sql, new Object[]{hpwd}, new int[]{Types.VARCHAR});
        session.setAttribute("user", uname);
        return new ModelAndView("redirect:intern.secu");
    }

    @RequestMapping(value = "/help.secu")
    public String showHelp() {
        return "howto";
    }

    private String hashen256(String mpwd) {
        String hpwd = null;

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            digest.update(mpwd.getBytes(), 0, mpwd.length());

            hpwd = new BigInteger(1, digest.digest()).toString(16);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }


        return hpwd;
    }


}
