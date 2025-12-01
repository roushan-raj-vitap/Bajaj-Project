package com.bfs.qualifier.service;
import org.springframework.stereotype.Service;

@Service
public class SqlSolverService {
    public String getSolution(int lastTwoDigits) {
        return "WITH valid_payments AS (\n    SELECT p.EMP_ID, SUM(p.AMOUNT) AS total_salary\n    FROM PAYMENTS p\n    WHERE EXTRACT(DAY FROM p.PAYMENT_TIME) != 1\n    GROUP BY p.EMP_ID\n),\nranked AS (\n    SELECT d.DEPARTMENT_NAME, vp.total_salary AS SALARY,\n           CONCAT(e.FIRST_NAME, ' ', e.LAST_NAME) AS EMPLOYEE_NAME,\n           EXTRACT(YEAR FROM AGE(CURRENT_DATE, e.DOB)) AS AGE,\n           ROW_NUMBER() OVER (\n               PARTITION BY d.DEPARTMENT_ID\n               ORDER BY vp.total_salary DESC\n           ) AS rn\n    FROM valid_payments vp\n    JOIN EMPLOYEE e ON e.EMP_ID = vp.EMP_ID\n    JOIN DEPARTMENT d ON d.DEPARTMENT_ID = e.DEPARTMENT\n)\nSELECT DEPARTMENT_NAME, SALARY, EMPLOYEE_NAME, AGE\nFROM ranked\nWHERE rn = 1\nORDER BY DEPARTMENT_NAME;\n";
    }
}
