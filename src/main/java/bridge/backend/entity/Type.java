package bridge.backend.entity;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.thymeleaf.util.StringUtils;

@Getter
@Slf4j
public enum Type {
    TYPE1(0,"사업화"),
    TYPE2( 1,"기술개발(R&D)"),
    TYPE3(2,"시설∙공간∙보육"),
    TYPE4(3,"멘토링∙컨설팅∙교육"),
    TYPE5(4,"행사∙네트워크"),
    TYPE6( 5,"융자"),
    TYPE7( 6,"인력"),
    TYPE8( 7,"글로벌 진출"),
    TYPE9(8,"공공기관"),
    TYPE10(9,"민간기관");

    private Integer idx;
    private String text;
    Type(Integer idx,String text){
        this.idx=idx;
        this.text=text;
    }

    public static Type fromText(String text){
        for(Type type : Type.values()){
            if(StringUtils.equals(type.getText(),text)) {
                return type;
            }
        }
        return null;
    }

    public static Type fromIdx(Integer idx){
        for(Type type : Type.values()){
            if(type.getIdx().equals(idx)) {
                return type;
            }
        }
        return null;
    }
}
