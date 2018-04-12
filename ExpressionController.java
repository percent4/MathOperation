package com.hello.operation.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.StringWriter;
import java.util.Map;
import org.python.util.PythonInterpreter;

@Controller

public class ExpressionController {

    @GetMapping("/mathoper")
    public String greetingForm(Model model) {
        model.addAttribute("expression", new Expression());
        return "operation";
    }

    @PostMapping("/mathoper")
    public String greetingSubmit(@ModelAttribute Expression expression, Map<String, Object> map) throws Exception {

        System.out.println(expression.getExpr());

        // 获取前端输入框中的表达式
        String expr = expression.getExpr();

        // 使用Python解释器
        PythonInterpreter interpreter = new PythonInterpreter();

        // 设置Python输出
        StringWriter out = new StringWriter();
        interpreter.setOut(out);

        // 执行Python代码
        interpreter.exec("import math");
        interpreter.exec("try:\n\tans=eval('"+expr+"')\nexcept Exception as e:\n\tans='Error: '+str(e)\nprint(ans)");

        // 获取Python运行的结果
        String result =out.toString();
        System.out.println(result);

        // Python运行结果是否出错，如正确，返回计算结果，如错误，返回错误提示信息
        if(result.indexOf("Error") == -1)
            map.put("answer", "The answer is "+result);
        else
            map.put("answer", "<mark>"+result+"</mark>");

        return "operation";
    }

}
