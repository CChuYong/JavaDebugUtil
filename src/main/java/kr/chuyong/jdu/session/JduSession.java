package kr.chuyong.jdu.session;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;

public class JduSession {
    public boolean printStack = false;
    private HashMap<String, Class<?>> classMap = new HashMap<>();
    private HashMap<String, Field> fieldMap = new HashMap<>();
    private HashMap<String, Object> objectMap = new HashMap<>();
    private HashMap<String, Method> methodMap = new HashMap<>();
    public void insertClass(String key, String className) throws Exception{
        Class<?> target = Class.forName(className);
        if(target!= null)
            classMap.put(key, target);
        else
            throw new UnsupportedOperationException("Target class is null");
    }
    public boolean insertField(String fieldKey, String classKey, String fieldName) throws Exception{
        Class<?> target = classMap.get(classKey);
        if(target == null)
            return false;
        else{
            Field f = target.getDeclaredField(fieldName);
            f.setAccessible(true);
            fieldMap.put(fieldKey, f);
            return true;
        }
    }
    public Object getObject(String key){
        return objectMap.get(key);
    }
    public boolean setField(String fieldKey, String instanceKey, String targetinst) throws Exception{
        Field f = fieldMap.get(fieldKey);
        Object inst = objectMap.get(instanceKey);
        if(f == null)
            return false;
        else{
            if(inst==null && !instanceKey.equalsIgnoreCase("null"))
                return false;
            Object target = objectMap.get(targetinst);
            if(targetinst.equalsIgnoreCase("null"))
                target = null;
            else if(target == null)
                return false;
            f.set(inst, target);
            return true;
        }
    }
    public boolean insertMethod(String methodKey, String classKey, String methodName){
        Class<?> target = classMap.get(classKey);
        if(target == null)
            return false;
        else{
            for(Method m : target.getDeclaredMethods()){
                if(m.getName().equalsIgnoreCase(methodName)){
                    m.setAccessible(true);
                    methodMap.put(methodKey, m);
                    return true;
                }
            }
            return false;
        }
    }
    public boolean insertObject(String objKey, String classKey, String... constructor) throws Exception{
        Class<?> target = classMap.get(classKey);
        if(target == null)
            return false;
        else{
            if(constructor.length == 0){
                Object obj = target.newInstance();
                if(obj == null)
                    throw new RuntimeException("new Instance is null");
                objectMap.put(objKey, obj);
                return true;
            }else{
              Object[] insts = new Object[constructor.length];
              int idx = 0;
              for(String cons : constructor){
                  Object obje = objectMap.get(cons);
                  if(obje == null && !cons.equals("null"))
                      throw new RuntimeException("constructor nullable exception");
                  insts[idx] = obje;
                  idx++;
              }
              mainloop: for(Constructor cons : target.getConstructors()){
                  if(cons.getParameterCount() == insts.length){
                      int cnt = 0;
                      for(Class<?> type : cons.getParameterTypes()){
                          if(!type.isInstance(insts[cnt++])){
                            continue mainloop;
                          }
                      }
                      Object tar = cons.newInstance(insts);
                      if(tar == null)
                          throw new RuntimeException("new Instance is null");
                      objectMap.put(objKey, tar);
                      return true;
                  }
              }
              throw new RuntimeException("Correct constructor not found!");
            }

        }
    }
    public boolean insertObject(String objKey, String fieldKey, String instanceKey) throws Exception{
        Field target = fieldMap.get(fieldKey);
        Object inst = objectMap.get(instanceKey);
        if(target == null)
            return false;
        else{
            Object obj = target.get(inst);
            if(obj == null)
                throw new RuntimeException("curr Instance is null");
            objectMap.put(objKey, obj);
            return true;
        }
    }
    public boolean insertObjectByMethod(String objKey, String methodKey) throws Exception{
        Method method = methodMap.get(methodKey);
        if(method == null)
            return false;
        else{
            Object obj = method.invoke(null);
            if(obj == null)
                throw new RuntimeException("curr Instance is null");
            objectMap.put(objKey, obj);
            return true;
        }
    }
    public boolean insertObjectByMethodDynamic(String objKey, String methodKey, String instKey, String[] instance) throws Exception{
        Method method = methodMap.get(methodKey);
        Object inst = objectMap.get(instKey);
        if(method == null || inst == null)
            return false;
        else{
            if(instance.length >= 5){
                Object[] objs = new Object[instance.length - 4];
                int index = 0;
                for(int i = 4; i < instance.length; i++){
                    Object obj = objectMap.get(instance[i]);
                    if(obj == null && !instance[i].equalsIgnoreCase("null"))
                        return false;
                    objs[index] = obj;
                    index++;
                }
                Object inv = method.invoke(inst, objs);
                if(inv == null)
                    throw new RuntimeException("curr Instance is null");
                objectMap.put(objKey, inv);
                return true;
            }else{
                Object inv = method.invoke(inst);
                if(inv == null)
                    throw new RuntimeException("curr Instance is null");
                objectMap.put(objKey, inv);
                return true;
            }
        }
    }
    public boolean invokeMethod(String methodName, String inst,  String[] instance) throws Exception{
        Method method = methodMap.get(methodName);
        Object objx = objectMap.get(inst);
        if(method == null || objx == null)
            return false;
        else{
            if(instance.length >= 4){
                Object[] objs = new Object[instance.length - 3];
                int index = 0;
                for(int i = 3; i < instance.length; i++){
                    Object obj = objectMap.get(instance[i]);
                    if(obj == null && !instance[i].equalsIgnoreCase("null"))
                        return false;
                    objs[index] = obj;
                    index++;
                }
                method.invoke(objx, objs);
                return true;
            }else{
                method.invoke(objx);
                return true;
            }
        }
    }
    public void insertObjectData(String key, Object val){
        objectMap.put(key, val);
    }
    public void resetSession(){
        classMap.clear();
        fieldMap.clear();
        objectMap.clear();
        methodMap.clear();
    }
}
