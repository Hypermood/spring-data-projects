package course.springdata.mvcproject.service.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
public class RoleServiceModel {
    private String authority;

    public RoleServiceModel() {
    }
}
