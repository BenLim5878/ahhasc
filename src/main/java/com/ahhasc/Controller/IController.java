package com.ahhasc.Controller;

import com.ahhasc.Model.Repository;
import java.util.ArrayList;

public interface IController {

    void Serialize();
    Repository GetRepository();
    ArrayList GetData();
}
