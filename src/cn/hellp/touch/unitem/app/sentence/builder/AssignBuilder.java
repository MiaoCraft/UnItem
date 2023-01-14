package cn.hellp.touch.unitem.app.sentence.builder;

import cn.hellp.touch.unitem.app.ActuatorList;
import cn.hellp.touch.unitem.app.AssignCallableActuator;
import cn.hellp.touch.unitem.app.PlaceholderManager;
import cn.hellp.touch.unitem.app.sentence.SentenceFactory;
import cn.hellp.touch.unitem.selector.Var;

import java.util.regex.Pattern;


public class AssignBuilder extends SentenceBuilder{
    public AssignBuilder(){
        pattern= Pattern.compile("\\$(?<placeholder>\\w+)?=(?<value>.+)");
    }

    @Override
    public Result create(String raw, PlaceholderManager placeholderManager, ActuatorList actuatorList) {
        String name = matcher.group("placeholder");
        Var var;
        if(placeholderManager.contains(name)) {
            var = placeholderManager.getVar(name);
        }else{
            var = new Var(null);
            placeholderManager.putVar(name,var);
        }
        AssignCallableActuator actuator = new AssignCallableActuator(var,SentenceFactory.getValue(matcher.group("value"),placeholderManager));
        return ResultType.DONE.withCallableActuator(actuator);
    }
}
