package com.ahhasc.Controller;

import com.ahhasc.Model.Repository;
import java.util.ArrayList;

public interface IController {
    Repository _repository = null;
    ArrayList<Object> _data = new ArrayList<Object>();

    void Serialize();
}
