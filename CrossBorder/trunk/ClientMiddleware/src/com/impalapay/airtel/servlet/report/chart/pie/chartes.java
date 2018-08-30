package com.impalapay.airtel.servlet.report.chart.pie;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
 
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
import com.google.gson.Gson;
import com.impalapay.airtel.servlet.report.chart.pie.StudentJsonDataServle;
 

public class chartes extends HttpServlet {
 
 private static final long serialVersionUID = 1L;
 
 public chartes() {
  super();
 }
 
 protected void doGet(HttpServletRequest request,
   HttpServletResponse response) throws ServletException, IOException {
 
  List<StudentJsonDataServle> listOfStudent = getStudentData();
 
  Gson gson = new Gson();
 
  String jsonString = gson.toJson(listOfStudent);
 
  response.setContentType("application/json");
 
  response.getWriter().write(jsonString);
 
 }
 
 private List<StudentJsonDataServle> getStudentData() {
 
  List<StudentJsonDataServle> listOfStudent = new ArrayList<StudentJsonDataServle>();
  StudentJsonDataServle s1 = new StudentJsonDataServle();
  s1.setName("Sandeep");
  s1.setComputerMark(28);
  s1.setMathematicsMark(26);
  s1.setGeographyMark(91);
  s1.setHistoryMark(55);
  s1.setLiteratureMark(42);
  listOfStudent.add(s1);
 
  return listOfStudent;
 }
}